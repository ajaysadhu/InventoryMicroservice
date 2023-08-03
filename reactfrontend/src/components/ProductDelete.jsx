import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
  MDBCardImage
} from 'mdb-react-ui-kit';
import '../styles/ProductDelete.css';

const ProductDelete = () => {
  const [products, setProducts] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');

  const fetchData = () => {
    axios.get('http://localhost:9000/inventory/v1/products/search?keyword=CHESS&currentPage=1&pageSize=10')
      .then((response) => {
        setProducts(response.data);
        setErrorMessage('');
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
        setErrorMessage('Error fetching data. Please try again.');
      });
  };

  const handleDelete = (productId) => {
    axios.delete(`http://localhost:9000/inventory/v1/products/${productId}`)
      .then(() => {
        // Refresh the product list after successful deletion
        alert("Product Deleted")
        fetchData();
      })
      .catch((error) => {
        console.error('Error deleting product:', error);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className='procontainer'>
      <h2>Best of Products</h2>
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
      {products.productDTOList && products.productDTOList.map((product) => (
        <div className="product-card noHover" key={product.id}>
          {product.imageUrl && (
            <MDBCardImage
              className="image"
              src={product.imageUrl}
              position="top"
              alt={product.name}
            />
          )}
          <p className="product-card__brand">{product.name}</p>
          <p className="product-card__description">{product.description}</p>
          <p className="product-card__price">${product.unitPrice}</p>
          <button className="product-card__btn-wishlist">
            <svg viewBox="0 0 18 16" xmlns="http://www.w3.org/2000/svg">
              {/* Wishlist SVG path */}
            </svg>
          </button>
          <button className="button-75" onClick={() => handleDelete(product.id)}>Delete</button>
        </div>
      ))}
    </div>
  );
};

export default ProductDelete;
