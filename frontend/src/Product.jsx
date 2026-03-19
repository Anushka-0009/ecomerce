import React, { useEffect, useState, useContext } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import axios from 'axios'
import './Style.css'
import StatusButton from './StatusButton'
import BuynowBtn from './BuynowBtn'
import Navbar from './Navbar'
import InsaneFluidCursor from './InsaneFluidCursor'
import { CartContext } from './CartContext'

import Box from '@mui/material/Box'
import Rating from '@mui/material/Rating'
import StarIcon from '@mui/icons-material/Star'
import Typography from '@mui/material/Typography'

const labels = {
  0.5: 'Useless', 1: 'Useless+', 1.5: 'Poor', 2: 'Poor+',
  2.5: 'Ok', 3: 'Ok+', 3.5: 'Good', 4: 'Good+',
  4.5: 'Excellent', 5: 'Excellent+',
}

function getLabelText(value) {
  return `${value} Star${value !== 1 ? 's' : ''}, ${labels[value]}`
}

function YourRating() {
  const [value, setValue] = useState(null)
  const [hover, setHover] = useState(-1)

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', gap: '6px', mt: '6px' }}>
      <Typography sx={{ fontSize: '0.85rem', fontWeight: 700, color: 'rgba(255,255,255,0.7)' }}>
        Your Rating
      </Typography>

      <Box sx={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
        <Rating
          value={value}
          precision={0.5}
          getLabelText={getLabelText}
          onChange={(_, newValue) => setValue(newValue)}
          onChangeActive={(_, newHover) => setHover(newHover)}
          emptyIcon={<StarIcon style={{ opacity: 0.35, color: '#fff' }} />}
          sx={{
            fontSize: '1.6rem',
            '& .MuiRating-iconFilled': { color: '#ffd055' },
          }}
        />

        {value !== null && (
          <Typography sx={{ fontSize: '0.88rem', fontWeight: 600, color: '#00e0c0' }}>
            {labels[hover !== -1 ? hover : value]}
          </Typography>
        )}
      </Box>
    </Box>
  )
}

function Product() {

  // ✅ FIXED ORDER
  const { source, id } = useParams()

  const navigate = useNavigate()
  const { addToCart } = useContext(CartContext)

  const [product, setProduct] = useState(null)
  const [loading, setLoading] = useState(true)
  const [selectedImg, setSelectedImg] = useState(null)

  const normalizeProduct = (p, src) => ({
    id: p.id,
    title: p.title,
    description: p.description,
    price: p.price,
    image: p.thumbnail || p.images?.[0] || p.image,
    images: p.images || [p.thumbnail || p.image],
    rating: src === 'fake' ? p.rating?.rate : p.rating,
    stock: p.stock ?? 'N/A',
  })

  useEffect(() => {

    setLoading(true)

    let url = ''

    if (source === 'dummy') {
      url = `https://dummyjson.com/products/${id}`
    } 
    else if (source === 'fake') {
      url = `https://fakestoreapi.com/products/${id}`
    } 
    else {
      // ✅ DEFAULT = YOUR RAILWAY BACKEND
      url = `https://ecomerce-production-b944.up.railway.app/api/products/${id}`
    }

    axios.get(url)
      .then((res) => {
        const normalized = normalizeProduct(res.data, source)
        setProduct(normalized)
        setSelectedImg(normalized.image)
        setLoading(false)
      })
      .catch((err) => {
        console.error("API ERROR:", err)
        setLoading(false)
      })

  }, [id, source])

  const handleAddToCart = () => {
    addToCart(product)
    alert(`${product.title} added to cart ✅`)
  }

  if (loading) return <div>Loading...</div>
  if (!product) return <div>No product found</div>

  return (
    <div className="product-page">

      <InsaneFluidCursor />
      <Navbar />

      <div className="product-wrapper">
        <div className="product-content">

          <div className="product-left-col">
            <img src={selectedImg} alt={product.title} className="product-image" />
          </div>

          <div className="product-box">
            <h2>{product.title}</h2>
            <p>{product.description}</p>
            <p>Price: ${product.price}</p>

            <YourRating />

            <div className="btn-box">
              <BuynowBtn product={product} />
              <button onClick={handleAddToCart}>Add to Cart</button>
            </div>

          </div>

        </div>
      </div>

    </div>
  )
}

export default Product