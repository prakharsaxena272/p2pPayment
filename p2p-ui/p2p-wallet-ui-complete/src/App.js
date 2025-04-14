import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import Transfer from './pages/Transfer';
import Transactions from './pages/Transactions';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Dashboard />} />
      <Route path="/transfer" element={<Transfer />} />
      <Route path="/transactions" element={<Transactions />} />
    </Routes>
  );
}

export default App;
