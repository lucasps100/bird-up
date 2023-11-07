import "./App.css";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
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
import EditProfileForm from "./components/EditProfileForm";

import { refreshToken, logout } from "./services/authAPI.js";

const TIMEOUT_MILLISECONDS = 14 * 60 * 1000;

export const OPEN_AI_KEY = process.env.REACT_APP_OPEN_AI_KEY;
export const GOOGLE_KEY = process.env.REACT_APP_GOOGLE_KEY;
export const GOOGLE_CX = process.env.REACT_APP_GOOGLE_CX;

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

  return (
    <>
      <AuthContext.Provider value={auth}>
        <Router>
          <Nav />
          <Routes>
            <Route path="/" element={<Explore />} />
            <Route
              path="/flock"
              element={user ? <Flock /> : <Navigate to="/" replace={true} />}
            />
            <Route path="/error" element={<Error />} />
            <Route path="/*" element={<NotFound />} />
            <Route path="/explore/:query" element={<SearchResults />} />
            <Route
              path="/login"
              element={!user ? <Login /> : <Navigate to="/" replace={true} />}
            />
            <Route path="/search/:query" element={<SearchResults />}></Route>
            <Route path="/register" element={<Register />} />

            <Route path="/profile/:username" element={<Profile />} />
            <Route
              path="/post"
              element={user ? <PostForm /> : <Navigate to="/" replace={true} />}
            />
            <Route
              path="/edit/:postId"
              element={user ? <PostForm /> : <Navigate to="/" replace={true} />}
            />
            <Route path="/update" element={<EditProfileForm />} />
          </Routes>
        </Router>
      </AuthContext.Provider>
    </>
  );
}
