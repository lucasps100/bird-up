import { useState } from "react";
import { createProfile } from "../services/profileAPI";
import ValidationSummary from "./ValidationSummary";
import { useNavigate } from "react-router-dom";

export default function CreateProfileForm() {
  const { errors, setErrors } = useState([]);
  const navigate = useNavigate();

  const INITIAL_PROFILE = {
    firstName: "",
    lastName: "",
    bio: "",
  };

  const [profile, setProfile] = useState(INITIAL_PROFILE);

  const handleChange = (evt) => {
    const nextProfile = { ...profile };
    nextProfile[evt.target.name] = evt.target.value;
    setProfile(nextProfile);
  };

  const handleSubmit = (evt) => {
    evt.preventDefault();
    createProfile(profile).then((data) => {
      if (data?.errors) {
        setErrors(data.errors);
      } else {
        navigate("/");
      }
    });
  };

  return (
    <div>
      <ValidationSummary errors={errors} />

      <form onSubmit={handleSubmit}>
        <div>
          <h2 className="modal-title">New Profile</h2>
          <div className="form-group">
            <label htmlFor="firstName">First Name:</label>
            <input
              className="form-control"
              type="text"
              name="firstName"
              id="firstName"
              value={profile.firstName}
              onChange={handleChange}
            />
          </div>
          <div className="form-col">
            <div className="row form-group">
              <label htmlFor="lastName">Last Name:</label>
              <input
                className="form-control"
                type="text"
                name="lastName"
                id="lastName"
                value={profile.lastName}
                onChange={handleChange}
              />
            </div>
            <div className="row form-group">
              <label htmlFor="bio">About You:</label>
              <textarea
                className="form-control"
                type="text"
                name="bio"
                id="bio"
                value={profile.maximumPlayers}
                onChange={handleChange}
              />
            </div>
          </div>
          <div>
            <button type="submit" className="btn btn-primary">
              Save Changes
            </button>
          </div>
        </div>
      </form>
    </div>
  );
}
