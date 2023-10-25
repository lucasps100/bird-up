import { getProfileByUsername } from "../services/profileAPI";
import {useContext, useEffect, useState} from 'react';
import AuthContext from "../context/AuthContext";
import { useNavigate } from "react-router";


export default function Profile() {

    const { user } = useContext(AuthContext);
    const navigate = useNavigate();
    const [myProfile, setMyProfile] = useState();

    useEffect(() => {
        if(user) {
            getProfileByUsername(user.username)
            .then(setMyProfile)
            .then(console.log(myProfile));
        }
    }, [])

    return (
        <div className="profile container">
            {/* myProfile && (
            <h3>{myProfile.firstName} {myProfile.lastName}</h3>
            <p>Username: {myProfile.username}</p>
            <p>Followers: {myProfile.followers.length}</p>
            <p>Following: {myProfile.followees.length}</p>
            ) */}
        </div>

    );
}