import React from 'react'
import { Routes, Route } from 'react-router-dom'
import Product from './Product'
import Navbar from './Navbar'
import Home from './Home'
import Login from './Login'
import Register from './Register'
import OrderPage from "./OrderPage";
import YourOrdersPage from './YourOrdersPage';
import MyCart from "./MyCart";
import AdminDashboard from './AdminDashboard';
import Profile from './Profile';

function Routing() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/product/:source/:id" element={<Product />} />
      <Route path="/order" element={<OrderPage />} />
      <Route path="/navbar" element={<Navbar />} />
      <Route path="/MyCart" element={<MyCart />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/your-orders" element={<YourOrdersPage />} />
      <Route path="/admin" element={<AdminDashboard />} />
      <Route path="/profile" element={<Profile />} />
    </Routes>
  )
}

export default Routing