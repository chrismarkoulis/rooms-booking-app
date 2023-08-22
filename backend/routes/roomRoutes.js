import express from 'express';
const router = express.Router();
import {
  getRooms,
  getRoomById,
  createRoom,
  updateRoom,
  deleteRoom,
  createRoomReview,
  getTopRooms,
} from '../controllers/roomController.js';
import { protect, admin } from '../middleware/authMiddleware.js';
import checkObjectId from '../middleware/checkObjectId.js';

router.route('/').get(getRooms).post(protect, admin, createRoom);
router.route('/:id/reviews').post(protect, checkObjectId, createRoomReview);
router.get('/top', getTopRooms);
router
  .route('/:id')
  .get(checkObjectId, getRoomById)
  .put(protect, admin, checkObjectId, updateRoom)
  .delete(protect, admin, checkObjectId, deleteRoom);

export default router;