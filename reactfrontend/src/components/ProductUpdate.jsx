import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../styles/ProductAdd.css';

const ProductUpdate = () => {
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [productId, setProductId] = useState('');
  const [product, setProduct] = useState({
    name: '',
    description: '',
    unitPrice: 0,
    unitsInStock: 0,
    imageUrl: '',
  });

  useEffect(() => {
    if (productId !== '') {
      // Fetch product details for update
      axios
        .get(`http://localhost:9000/inventory/v1/product/${productId}`)
        .then((response) => {
          setProduct(response.data);
        })
        .catch((error) => {
          console.error('Error fetching product details:', error);
        });
    }
  }, [productId]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProduct((prevProduct) => ({
      ...prevProduct,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    axios
      .put(`http://localhost:9000/inventory/v1/products/${productId}`, product)
      .then(() => {
        setSuccessMessage('Product updated successfully!');
        setErrorMessage('');
      })
      .catch((error) => {
        console.error('Error updating product:', error);
        setErrorMessage('Error updating product. Please try again.');
        setSuccessMessage('');
      });
  };

  return (
    <div className='container'>
      {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}

      <h3>Update Product</h3>
      <form onSubmit={handleSubmit} className='formsub'>
        <label>
          <p>Product ID:</p>
          <input
            type='number'
            name='productId'
            value={productId}
            onChange={(e) => setProductId(parseInt(e.target.value))}
          />
        </label>
        <label>
          <p>Name:</p>
          <input
            type='text'
            name='name'
            value={product.name}
            onChange={handleInputChange}
          />
        </label>
        <label>
          <p>Description:</p>
          <input
            type='text'
            name='description'
            value={product.description}
            onChange={handleInputChange}
          />
        </label>
        <label>
          <p>Unit Price:</p>
          <input
            type='number'
            name='unitPrice'
            value={product.unitPrice}
            onChange={handleInputChange}
          />
        </label>
        <label>
          <p>Units in Stock:</p>
          <input
            type='number'
            name='unitsInStock'
            value={product.unitsInStock}
            onChange={handleInputChange}
          />
        </label>
        <label>
          <p>Image URL:</p>
          <input
            type='text'
            name='imageUrl'
            value={product.imageUrl}
            onChange={handleInputChange}
          />
        </label>
        <button className='button-71' type='submit'>
          Update Product
        </button>
      </form>
    </div>
  );
};

export default ProductUpdate;
