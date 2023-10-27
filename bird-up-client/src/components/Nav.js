import { NavLink, useNavigate } from "react-router-dom";
import { useState, useContext } from "react";
import AuthContext from "../context/AuthContext";

export default function Nav() {
  const [query, setQuery] = useState("");
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  function handleChange(evt) {
    setQuery(evt.target.value.replace(" ", "_"));
  }

  function handleSubmit(evt) {
    evt.preventDefault();
    navigate(`/search/${query}`);
  }

  function handleLogout(evt) {
    logout();
    navigate("/");
  }

  return (
    <header>
      <nav className="navbar navbar-expand-lg" id="nav">
        <NavLink id="title" to="/" className="navbar-brand">
          <img
            src="bird-up-logo.png"
            alt="bird up logo"
            height="50"
            className="logo mx-2"
          ></img>
          Bird Up
        </NavLink>
        <ul className="navbar-nav mr-auto">
          <div class="search nav-item ">
            <form onSubmit={handleSubmit} className="form-inline my-2">
              <input
                id="query"
                type="text"
                placeholder="Search..."
                name="query"
                onChange={handleChange}
              ></input>
              <button type="submit">
                <img
                  src="binoculars.svg"
                  width="22"
                  id="binoculars"
                  alt="binoculars search icon"
                ></img>
              </button>
            </form>
          </div>
          <li className="nav-item">
            <NavLink
              id="linkList"
              className="nav-link"
              to="/"
              activeClassName="underline"
            >
              Explore
            </NavLink>
          </li>
          {user ? (
            <>
              <li className="nav-item">
                <NavLink
                  id="linkPost"
                  to="/post"
                  className="nav-link"
                  activeClassName="underline"
                >
                  Share Sighting
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  id="linkProfile"
                  to={`/profile/${user.username}`}
                  className="nav-link"
                  activeClassName="underline"
                >
                  Profile
                </NavLink>
              </li>
              <li className="nav-item">
                <button onClick={handleLogout} className="nav-link">
                  Logout
                </button>
              </li>
            </>
          ) : (
            <>
              <li className="nav-item">
                <NavLink
                  id="login"
                  className="nav-link"
                  to="/login"
                  activeClassName="underline"
                >
                  Log In
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  id="register"
                  className="nav-link"
                  to="/register"
                  activeClassName="underline"
                >
                  Sign Up
                </NavLink>
              </li>
            </>
          )}
        </ul>
      </nav>
    </header>
  );
}
