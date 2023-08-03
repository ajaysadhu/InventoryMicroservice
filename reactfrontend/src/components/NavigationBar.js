import { Navbar, Nav } from 'rsuite';
import HomeIcon from '@rsuite/icons/legacy/Home';
import React, { useState } from 'react';
import { Input, InputGroup } from 'rsuite';
import SearchIcon from '@rsuite/icons/Search';
import "../styles/nav.css";
import "./Adminpanel";
import { useNavigate } from 'react-router-dom';
//import axios from 'axios';
//import {useEffect } from 'react';
const styles = {
    width: 300,
    marginTop:15,
    marginBottom:15
  };
  
const NavigationBar=({ onSelect, activeKey, ...props })=>{
    const [searchQuery, setSearchQuery] = useState('');
    
    const navigate = useNavigate();

    // const [categories, setCategories] = useState([]);
    // const fetchCategories = async () => {
    //   try {
    //     const response = await axios.get('http://localhost:9000/inventory/v1/categories');
    //     console.log(response.data);
    //     return response.data;
    //   } catch (error) {
    //     console.error('Error fetching categories:', error);
    //     return [];
    //   }
    // };
  
    // useEffect(() => {
    //   fetchCategories().then((fetchedCategories) => {
    //     setCategories(fetchedCategories);
    //   });
    // }, []);
  

    const handleSearch = () => {
      navigate(`/search/${searchQuery}`);
    }
    const handleCategoryClick = (categoryName) => {
      navigate(`/search/${categoryName}`);
    };
    const handleKeyPress = (event) => {
        if (event.charCode === 13) {
          handleSearch();
        }
      };
    return (
        <Navbar {...props}>
      <Navbar.Brand >Product Example</Navbar.Brand>
      <Nav onSelect={onSelect} activeKey={activeKey}>
        <Nav.Item eventKey="1" href="/" icon={<HomeIcon />}>
          Home
        </Nav.Item>
        <Nav.Item eventKey="3" href="/Adminpanel">Admin</Nav.Item>
        <Nav.Menu title="Categories">
        {/* {categories.map((category) => (
            <Nav.Item key={category.id} eventKey={category.id} onClick={() => handleCategoryClick(category.name)}>
              {category.name}
            </Nav.Item>
          ))} */}
          <Nav.Item eventKey="4" onClick={() => handleCategoryClick('CHESS')}>CHESS</Nav.Item>
          <Nav.Item eventKey="5" onClick={() => handleCategoryClick('BOARD')}>BOARD</Nav.Item>
        </Nav.Menu>       
      </Nav>
      <InputGroup inside style={styles}>
        <Input value={searchQuery} onChange={value => setSearchQuery(value)} onKeyPress={handleKeyPress}/>
        <InputGroup.Button>
        <SearchIcon />
        </InputGroup.Button>
        </InputGroup>
    </Navbar>
  );
}

export default NavigationBar;