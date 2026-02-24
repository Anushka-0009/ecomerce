import { createContext, useState, useEffect } from "react";
import axios from "axios";

export const CartContext = createContext();

export const CartProvider = ({ children }) => {

  const [cartItems, setCartItems] = useState([]);

  // Load cart from backend
  useEffect(() => {
    loadCart();
  }, []);

  const loadCart = async () => {
    try {
      const response = await axios.get("http://localhost:8080/cart");
      setCartItems(response.data);
    } catch (error) {
      console.log("Error loading cart:", error);
    }
  };

  // Add product
  const addToCart = async (product) => {
    try {

      const newProduct = {
        title: product.title,
        price: product.price,
        image: product.image
      };

      await axios.post("http://localhost:8080/cart", newProduct);

      loadCart(); // refresh cart

    } catch (error) {
      console.log("Error adding product:", error);
    }
  };

  // Remove product
  const removeFromCart = async (id) => {
    try {

      await axios.delete(`http://localhost:8080/cart/${id}`);

      loadCart();

    } catch (error) {
      console.log("Error deleting:", error);
    }
  };

  return (
    <CartContext.Provider value={{ cartItems, addToCart, removeFromCart }}>
      {children}
    </CartContext.Provider>
  );
};