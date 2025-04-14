import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

export const fetchBalance = createAsyncThunk(
  'wallet/fetchBalance',
  async (userId) => {
    const response = await axios.get(`/api/wallet/balance/${userId}`);
    return response.data;
  }
);

const walletSlice = createSlice({
  name: 'wallet',
  initialState: {
    balance: 0,
    status: 'idle',
  },
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(fetchBalance.fulfilled, (state, action) => {
        state.balance = action.payload;
        state.status = 'succeeded';
      });
  },
});

export default walletSlice.reducer;
