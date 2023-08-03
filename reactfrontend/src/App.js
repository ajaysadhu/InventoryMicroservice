import './App.css';
import Adminpanel from './components/Adminpanel';
import Homepage from './components/Homepage';
import NavigationBar from './components/NavigationBar';
import {BrowserRouter as Router, Routes ,Route } from 'react-router-dom';
import SearchResult from './components/SearchResult';
function App() {
  return (
    <div className="App">
      <Router>
      <NavigationBar activeKey="1" />
      <Routes>
        <Route path="/" element={<Homepage />}/>
        <Route path="/Adminpanel" element={<Adminpanel/>}/>
        <Route path="/search/:searchTerm" element={<SearchResult/>}/>
      </Routes>
      </Router>
    </div>
  );
}

export default App;
