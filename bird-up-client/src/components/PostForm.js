import { useEffect, useState, React} from "react";
import { Link, useNavigate } from "react-router-dom";
import Select from "react-select";

import {savePost} from "../services/postsAPI";
import { findAllSpecies } from "../services/speciesAPI";
import ValidationSummary from "./ValidationSummary";

const INITIAL_POST = {
    postId: 0,
    postText: ""
  };
  

export default function CreatePost() {
    const [post, setPost] = useState(INITIAL_POST);
    const [species, setSpecies] = useState([]);
    const [selectedSpecies, setSelectedSpecies] = useState();
    const [errors, setErrors] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        findAllSpecies().then(setSpecies);
        console.log(species);
    }, [])

    const handleTextChange = (evt) => {
            const nextPost = { ...post };
            nextPost[evt.target.name] = evt.target.value;
            setPost(nextPost);
        };
    
      const handleSelectChange= (s)  => {
        console.log(s)
        setSelectedSpecies(s);
      };


      const handleSubmit = (evt) => {
        evt.preventDefault();
          savePost(post, selectedSpecies.value).then((data) => {
          if (data?.errors) {
            setErrors(data.errors);
          } else {
            navigate("/", {
              state: { message: `Your Sighting was posted.` },
            });
          }
        });
      };

      return (
        <div>
          <ValidationSummary errors={errors} />
          <h2 className="modal-title">
                "New Sighting"
              </h2>
          <form onSubmit={handleSubmit}>
            <div>
              <div className="form-row">
                <div className="col form-group">
                  <label htmlFor="speciesId">Species</label>

                <Select required className="form-control" name="speciesId"
                onChange={handleSelectChange} value={selectedSpecies}
                options={species.map(s => ({"label": s.speciesShortName, "value": s.speciesId}))}>

                </Select>

                </div>
              </div>
              <div className="form-group">
                <label htmlFor="postText">Description</label>
                <textarea
                  className="form-control"
                  name="postText"
                  id="postText"
                  value={post.postText}
                  onChange={handleTextChange}
                />
              </div>
              
              <div>
                <Link to="/" className="btn btn-secondary">
                  Cancel
                </Link>
                <button type="submit" className="btn btn-primary">
                  Save changes
                </button>
              </div>
            </div>
          </form>
        </div>
      );








}