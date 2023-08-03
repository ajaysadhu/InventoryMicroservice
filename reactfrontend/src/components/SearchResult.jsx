import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { MDBCardImage } from 'mdb-react-ui-kit';

const SearchResult = () => {
  const { searchTerm } = useParams();
  const [products, setProducts] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');

  const fetchData = () => {
    axios
      .get(
        `http://localhost:9000/inventory/v1/products/search?keyword=${searchTerm}&currentPage=1&pageSize=100`
      )
      .then((response) => {
        setProducts(response.data);
        setErrorMessage('');
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
        setErrorMessage('Error fetching data. Please try again.');
      });
  };

  useEffect(() => {
    fetchData();
  }, [searchTerm]);

  return (
    <div>
      <h3>Search Results for "{searchTerm}"</h3>
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}

      {products.productDTOList && products.productDTOList.length === 0 ? (
        <p>No products found.</p>
      ) : (
        products.productDTOList && products.productDTOList.map((product) => (
          <a className="product-card noHover" href="#Product" key={product.id}>
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
                <path
                  d="M9.01163699,14.9053769 C8.72930024,14.7740736 8.41492611,14.6176996 8.07646224,14.4366167 C7.06926649,13.897753 6.06198912,13.2561336 5.12636931,12.5170512 C2.52930452,10.4655288 1.00308384,8.09476443 1.00000218,5.44184117 C0.997549066,2.99198843 2.92175104,1.01242822 5.28303025,1.01000225 C6.41066623,1.00972036 7.49184369,1.4629765 8.28270844,2.2678673 L8.99827421,2.9961237 L9.71152148,2.26559643 C10.4995294,1.45849728 11.5791258,1.0023831 12.7071151,1.00000055 L12.7060299,1.00000225 C15.0693815,0.997574983 16.9967334,2.97018759 17.0000037,5.421337 C17.0038592,8.07662382 15.4809572,10.4530151 12.8850542,12.5121483 C11.9520963,13.2521931 10.9477036,13.8951276 9.94340074,14.4354976 C9.60619585,14.6169323 9.29297309,14.7736855 9.01163699,14.9053769 Z"
                  strokeWidth="2"
                />
              </svg>
            </button>
            <button className="button-71">Buy</button>
          </a>
        ))
      )}
    </div>
  );
};

export default SearchResult;
