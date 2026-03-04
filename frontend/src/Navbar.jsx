// Navbar.jsx
import React from "react";
import DynamicNavigation from "./DynamicNavigation.jsx";
import SearchBar from "./SearchBar.jsx";
import './Style.css'
import { Link } from "react-router-dom";

export default function Navbar({ onSearch }) {
  const user = JSON.parse(localStorage.getItem("user"));
  const isAdmin = user && user.role === "ADMIN";

  const links = [
    { id: "home", label: "Home", href: "/" },
    { id: "cart", label: "My Cart", href: "/MyCart" },
    { id: "orders", label: "Your Orders", href: "/your-orders" },
  ];

  if (user) {
    links.push({ id: "profile", label: "Profile", href: "/profile" });
    if (isAdmin) {
      links.push({ id: "admin", label: "Admin", href: "/admin" });
    }
  } else {
    links.push({ id: "login", label: "Login", href: "/login" });
  }

  return (
    <div className="Navbar">
      <Link to="/" style={{ textDecoration: "none", color: "white", fontWeight: "bold", fontSize: "2rem", marginLeft: "10px" }} className="page-Logo">Monocart</Link>

      {/* Search bar */}
      <SearchBar onSearch={onSearch} />

      {/* Navigation links */}
      <DynamicNavigation
        links={links}
        showLabelsOnMobile={true}
      />
    </div>
  );
}
