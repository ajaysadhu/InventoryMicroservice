import React, { useState} from 'react';
import axios from 'axios';
import "../styles/Category.css"
const CategoryAdd = () => {
  const [formData, setFormData] = useState({
    categoryName: '',
  });

  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  // const [products, setProducts] = useState([]); 

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const sendDataToBackend = (data) => {
    axios.post('http://localhost:9000/inventory/v1/category', data)
      .then((response) => {
        setSuccessMessage('Category added successfully!');
        setErrorMessage(''); // Clear any previous error message
      })
      .catch((error) => {
        setErrorMessage('Error adding category. Please try again.');
        setSuccessMessage(''); 
      });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    sendDataToBackend(formData);
  };

  // const fetchData = () => {
  //   axios.get('http://localhost:9000/inventory/v1/products/search?keyword=CHESS&currentPage=1&pageSize=5')
  //     .then((response) => {
  //       setProducts(response.data);
  //       console.log('Data fetched successfully!', response.data);
  //       setSuccessMessage('Data fetched successfully!');
  //       setErrorMessage('');
        
  //     })
  //     .catch((error) => {
  //       console.error('Error fetching data:', error);
  //       setErrorMessage('Error fetching data. Please try again.');
  //       setSuccessMessage(''); 
  //     });
  // };

  // useEffect(() => {
  //   fetchData();
  // }, []);
  
  return (
    <div className="container">
    <h5>{successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}</h5>
      <h5>{errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}</h5>
    <div className="categoryadd">
    <form onSubmit={handleSubmit}>
        <h3>Add Product Category</h3>
      <label>
      <p>Category Name:</p>
        <input
          type="text"
          name="categoryName"
          value={formData.categoryName}
          onChange={handleChange}
        />
      </label>
      <button className="button-71"type="submit">Submit</button>
    </form>
    </div>

   {/* <div class="categoryget">
    <h3>Get All Categories</h3>
      <button className="button-72" onClick={fetchData}>Fetch Data</button>
      <ul>
        {products.map((product) => (
          <li key={product.id}>
            {product.categoryName}
          </li>
        ))}
      </ul>
    </div>  */}
    
    </div>
  );
};

export default CategoryAdd;