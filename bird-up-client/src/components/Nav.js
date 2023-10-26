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
          <div class="search nav-item form-inline">
            <form onSubmit={handleSubmit} className="form-inline my-2 my-lg-0">
              <input
                id="query"
                type="text"
                placeholder="Search..."
                name="query"
                onChange={handleChange}
              ></input>
              <button type="submit">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  className="bi bi-search"
                  viewBox="0 0 16 16"
                >
                  <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
                </svg>
              </button>
            </form>
          </div>
          <li className="nav-item">
            <NavLink id="linkList" className="nav-link" to="/">
              Explore
            </NavLink>
          </li>
          {user ? (
            <>
              {/* <li className="nav-item">
                    Welcome {user.username}!
                </li> */}
              <li className="nav-item">
                <NavLink id="linkPost" to="/post" className="nav-link">
                  Share Sighting
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  id="linkProfile"
                  to={`/profile/${user.username}`}
                  className="nav-link"
                >
                  Profile
                </NavLink>
              </li>
              <li className="nav-item">
                <button onClick={logout} className="nav-link">
                  Logout
                </button>
              </li>
            </>
          ) : (
            <>
              <li className="nav-item">
                <NavLink id="login" className="nav-link" to="/login">
                  Log In
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink id="register" className="nav-link" to="/register">
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
