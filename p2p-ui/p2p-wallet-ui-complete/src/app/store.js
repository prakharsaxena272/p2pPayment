import { configureStore } from '@reduxjs/toolkit';
import userReducer from '../features/user/userSlice';
import walletReducer from '../features/wallet/walletSlice';
import paymentReducer from '../features/payment/paymentSlice';
import transactionReducer from '../features/transaction/transactionSlice';

export default configureStore({
  reducer: {
    user: userReducer,
    wallet: walletReducer,
    payment: paymentReducer,
    transaction: transactionReducer,
  },
});
