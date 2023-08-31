import mongoose from 'mongoose';

const reviewSchema = mongoose.Schema(
    {
      name: { type: String, required: true },
      rating: { type: Number, required: true },
      comment: { type: String, required: true },
      user: {
        type: mongoose.Schema.Types.ObjectId,
        required: true,
        ref: 'User',
      },
    },
    {
      timestamps: true,
    }
  );

const roomSchema = mongoose.Schema(
    {
        user: {
            type: mongoose.Schema.Types.ObjectId,
            required: true,
            ref: 'User',
        },
        name: {
            type: String,
            required: true,
        },
        image: {
            type: String,
            required: false,
        },
        location: {
            type: String,
            required: false,
        },
        capacity: {
            type: Number,
            required: false,
            default: 2,
        },
        description: {
            type: String,
            required: true,
        },
        rating: {
            type: Number,
            required: false,
            default: 0,
        },
        reviews: [reviewSchema],
        numReviews: {
            type: Number,
            required: false,
            default: 0,
        },
        price: {
            type: Number,
            required: true,
            default: 0,
        },
        start: {
            type: Date,
            required: false,
        },
        end: {
            type: Date,
            required: false,
        },
        facilities: [String],
        amenities: [String],
    },
    {
        timestamps: true,
    }
);

const Room = mongoose.model('Room', roomSchema);

export default Room;