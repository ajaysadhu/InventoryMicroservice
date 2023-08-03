import React, { useState } from 'react';
import axios from 'axios';
import '../styles/ProductAdd.css'
const ProductAdd = () => {
  
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [newProduct, setNewProduct] = useState({
    sku: '',
    name: '',
    description: '',
    unitPrice: 0,
    imageUrl: '',
    active: 1,
    unitsInStock: 0,
    category: {
      id: '',
      categoryName: '',
    },
  });
  
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewProduct((prevProduct) => ({
      ...prevProduct,
      [name]: value,
    }));
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post('http://localhost:9000/inventory/v1/product', newProduct)
      .then(() => {
        setNewProduct({
          sku: '',
          name: '',
          description: '',
          unitPrice: 0,
          imageUrl: '',
          active: 1,
          unitsInStock: 0,
          category: {
            id: '',
            categoryName: '',
          },
        });
        setSuccessMessage('Product added successfully!');
        setErrorMessage('');
        
      })
      .catch((error) => {
        console.error('Error adding product:', error);
        setErrorMessage('Error adding product. Please try again.');
        setSuccessMessage('');
      });
  };
  return (
    <div className='container'>
      <h5>{successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}</h5>
      <h5>{errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}</h5>
      <h3>Add New Product</h3>
      <form onSubmit={handleSubmit} className="formsub">
        <label>
         <p> SKU:</p>
          <input type="text" name="sku" value={newProduct.sku}
            onChange={handleInputChange}
          />
        </label>
        <label>
          <p>Name:</p>
          <input type="text" name="name" value={newProduct.name}
            onChange={handleInputChange}
          />
        </label>
        <label>
        <p>Description:</p>
          <input type="text" name="description" value={newProduct.description}
            onChange={handleInputChange}
          />
        </label>
        <label>
        <p>Unit Price:</p>
          <input type="number" name="unitPrice" value={newProduct.unitPrice}
            onChange={handleInputChange}
          />
        </label>
        <label>
        <p>Image URL:</p>
          <input type="text" name="imageUrl" value={newProduct.imageUrl}
            onChange={handleInputChange}
          />
        </label>
        <label>
        <p>Units in Stock:</p>
          <input type="number" name="unitsInStock" value={newProduct.unitsInStock}
            onChange={handleInputChange}
          />
        </label>
        <label>
        <p>Category ID:</p>
          <input type="text"  name="id" value={newProduct.category.id}
            onChange={(e) => setNewProduct((prevProduct) => ({
              ...prevProduct,
              category: { ...prevProduct.category, id: e.target.value },
            }))}
          />
        </label>
        <label>
        <p>Category Name:</p>
          <input type="text" name="categoryName" value={newProduct.category.categoryName}
            onChange={(e) => setNewProduct((prevProduct) => ({
              ...prevProduct,
              category: { ...prevProduct.category, categoryName: e.target.value },
            }))}
          />
        </label>
        <button className='button-71' type="submit">Add Product</button>
      </form>
    </div>
  );
};

export default ProductAdd;

