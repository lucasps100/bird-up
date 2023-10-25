import './App.css';
import {BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import { useState, useEffect, useCallback } from "react";

import AuthContext from "./context/AuthContext";
import Explore from "./components/Explore.js";
import Flock from "./components/Flock.js";
import NotFound from "./components/NotFound.js";
import Error from "./components/Error.js";
import SearchResults from "./components/SearchResults.js";
import Profile from "./components/Profile.js";
import Nav from "./components/Nav.js";
import Login from "./components/Login.js";
import Register from "./components/Register.js";
import PostForm from "./components/PostForm";

import { refreshToken, logout } from "./services/authAPI.js";
// Import the functions you need from the SDKs you need


const TIMEOUT_MILLISECONDS = 14 * 60 * 1000;


export default function App() {

  const [user, setUser] = useState();
  const [initialized, setInitialized] = useState(false);

  const resetUser = useCallback(() => {
    refreshToken()
      .then((user) => {
        setUser(user);
        setTimeout(resetUser, TIMEOUT_MILLISECONDS);
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => setInitialized(true));
  }, []);

  useEffect(() => {
    resetUser();
  }, [resetUser]);

  const auth = {
    user: user,
    handleLoggedIn(user) {
      setUser(user);
      setTimeout(resetUser, TIMEOUT_MILLISECONDS);
    },
    hasAuthority(authority) {
      return user?.authorities.includes(authority);
    },
    logout() {
      logout();
      setUser(null);
    },
  };

  if (!initialized) {
    return null;
  }

  // const renderWithAuthority = (Component, ...authorities) => {
  //   for (let authority of authorities) {
  //     if (auth.hasAuthority(authority)) {
  //       return <Component />;
  //     }
  //   }
  //   return <Error />;
  // };

  return (
    <>
      <AuthContext.Provider value={auth}>
        <Router>
          <Nav />
          <Routes>
            <Route path="/" element={<Explore />}/>
             <Route path="/flock" element={<Flock />}/>
            <Route path="/error" element= { <Error />}/>
            <Route path="/*" element={ <NotFound />} />
            <Route path="/search/:query" element={<SearchResults/>} />
            <Route path="/login" element={!user ? <Login/> : <Navigate to="/" replace={true} />}  />
            <Route path="/register" element={<Register/>}/>
            <Route path="/profile" element={<Profile />}/> 
            <Route path="/post" element={<PostForm />}/> 


          </Routes>
        </Router>
      </AuthContext.Provider>
    </>
  );
}

