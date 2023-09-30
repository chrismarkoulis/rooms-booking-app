import asyncHandler from 'express-async-handler';
import Room from '../models/roomModel.js';
import User from '../models/userModel.js'

// @desc    Fetch all rooms
// @route   GET /api/rooms
// @access  Public
const getRooms = asyncHandler(async (req, res) => {
  const pageSize = process.env.PAGINATION_LIMIT;
  const page = Number(req.query.pageNumber) || 1;

  const keyword = req.query.keyword
    ? {
        name: {
          $regex: req.query.keyword,
          $options: 'i',
        },
      }
    : {};

  const count = await Room.countDocuments({ ...keyword });
  const rooms = await Room.find({ ...keyword })
    .limit(50);

  /**
   * TODO: PAGINATION FOR CLIENT
   */
  //res.json({ rooms, page, pages: Math.ceil(count / pageSize) });

  res.json({ rooms });
});

// @desc    Fetch single room
// @route   GET /api/rooms/:id
// @access  Public
const getRoomById = asyncHandler(async (req, res) => {
  // NOTE: checking for valid ObjectId to prevent CastError moved to separate
  // middleware. See README for more info.

  const room = await Room.findById(req.params.id);
  if (room) {
    return res.json(room);
  } else {
    // NOTE: this will run if a valid ObjectId but no room was found
    // i.e. room may be null
    res.status(404);
    throw new Error('Room not found');
  }
});

// @desc    Create a room
// @route   POST /api/rooms
// @access  Private/Admin
const createRoom = asyncHandler(async (req, res) => {
  // const room = new Room({
  //   name: 'Sample name',
  //   price: 0,
  //   user: req.user._id,
  //   image: 'https://via.placeholder.com/600/24f355',
  //   location: 'Athens, Greece',
  //   capacity: 2,
  //   numReviews: 0,
  //   rating: 0,
  //   description: 'Sample description',
  //   start: new Date(),
  //   end: new Date(),
  //   facilities: ["Bedroom", "Kitchen", "Bathroom"],
  //   amenities: ["WiFi", "Breakfast", "Kitchen Equipment"]
  // });
  const { name, location, description, price } = req.body;

  const roomExists = await Room.findOne({ name });
  const userObject = await User.findById(req.user._id)
  
    if (roomExists) {
      res.status(400);
      throw new Error('Room already exists');
    }

  const room = await Room.create({
    user: req.user._id,
    userObject, 
    name,
    location,
    description,
    price
  });

  const createdRoom = await room.save();
  res.status(201).json(createdRoom);
});

// @desc    Update a room
// @route   PUT /api/rooms/:id
// @access  Private/Admin
const updateRoom = asyncHandler(async (req, res) => {
  const { 
     name,
     price, 
     description, 
     image,
     location,
     capacity,
     start,
     end,
     facilities,
     amenities 
    } =
    req.body;

  const room = await Room.findById(req.params.id);

  if (room) {
    room.name = name;
    room.price = price;
    room.description = description;
    room.image = image;
    room.location = location,
    room.capacity = capacity,
    room.start = start,
    room.end = end,
    room.facilities = facilities,
    room.amenities = amenities

    const updatedRoom = await room.save();
    res.json(updatedRoom);
  } else {
    res.status(404);
    throw new Error('Room not found');
  }
});

// @desc    Delete a room
// @route   DELETE /api/rooms/:id
// @access  Private/Admin
const deleteRoom = asyncHandler(async (req, res) => {
  const room = await Room.findById(req.params.id);

  if (room) {
    await Room.deleteOne({ _id: room._id });
    res.json({ message: 'Room removed' });
  } else {
    res.status(404);
    throw new Error('Room not found');
  }
});

// @desc    Create new review
// @route   POST /api/rooms/:id/reviews
// @access  Private
const createRoomReview = asyncHandler(async (req, res) => {
  const { rating, comment } = req.body;

  const room = await Room.findById(req.params.id);

  if (room) {
    const alreadyReviewed = room.reviews.find(
      (r) => r.user.toString() === req.user._id.toString()
    );

    if (alreadyReviewed) {
      res.status(400);
      throw new Error('Room already reviewed');
    }

    const review = {
      name: req.user.name,
      rating: Number(rating),
      comment,
      user: req.user._id,
    };

    room.reviews.push(review);

    room.numReviews = room.reviews.length;

    room.rating =
      room.reviews.reduce((acc, item) => item.rating + acc, 0) /
      room.reviews.length;

    await room.save();
    res.status(201).json({ message: 'Review added' });
  } else {
    res.status(404);
    throw new Error('Room not found');
  }
});

// @desc    Get top rated rooms
// @route   GET /api/rooms/top
// @access  Public
const getTopRooms = asyncHandler(async (req, res) => {
  const rooms = await Room.find({}).sort({ rating: -1 }).limit(3);

  res.json(rooms);
});

export {
  getRooms,
  getRoomById,
  createRoom,
  updateRoom,
  deleteRoom,
  createRoomReview,
  getTopRooms,
};