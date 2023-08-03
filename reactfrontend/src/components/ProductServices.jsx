import React, { useState } from 'react';
import  ProductService from "./ProductAdd";
import '../styles/ProductService.css'
import ProductUpdate from './ProductUpdate';
import ProductDelete from './ProductDelete';
const ProductServices=()=>{
    const [selectedService, setSelectedService] = useState('');

    const handleServiceClick = (service) => {
      setSelectedService(service);
    };
    return (
        <div className="container-1">
          <h5>Product Services</h5>
          <ul>
            <li>
              <button className='button-33' onClick={() => handleServiceClick('Product Services')}>
                Product Add
              </button>
            </li>
            <li>
              <button className='button-33' onClick={() => handleServiceClick('Product Update')}>
                Product Update
              </button>
            </li>
            <li>
              <button className='button-33' onClick={() => handleServiceClick('Product Delete')}>
                Product Delete
              </button>
            </li>
          </ul>
          {selectedService && (
            <div>
              {/* <h3>Selected Service: {selectedService}</h3> */}
              {selectedService === 'Product Services' && <ProductService />}
              {selectedService === 'Product Update' && <ProductUpdate />}
              {selectedService === 'Product Delete' && <ProductDelete />}
            </div>
          )}
        </div>
      );
};
export default ProductServices;