import React, { useState } from 'react';
import  ProductService from "./ProductServices";
import  CategoryService  from "./CategoryAdd";
import { Panel } from 'rsuite';
import '../styles/Homepage.css'
const Adminpanel = () => {
  const [selectedService, setSelectedService] = useState('');

  const handleServiceClick = (service) => {
    setSelectedService(service);
  };

  return (
    <div className="container-1">
      <h3>Admin Panel</h3>
      <Panel shaded>
      <ul>
        <li>
          <button className='button-71' onClick={() => handleServiceClick('Product Services')}>
            Product Services
          </button>
        </li>
        <li>
          <button className='button-71' onClick={() => handleServiceClick('Category Services')}>
            Category Services
          </button>
        </li>
      </ul>
      </Panel>
      

      {selectedService && (
        <div>
          {/* <h3>Selected Service: {selectedService}</h3> */}
          {selectedService === 'Product Services' && <ProductService />}
          {selectedService === 'Category Services' && <CategoryService />}
        </div>
      )}
    </div>
  );
};



export default Adminpanel;
