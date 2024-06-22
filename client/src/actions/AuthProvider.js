import { createContext, useContext, useEffect, useState } from "react";
import { signInApiCall } from "../service/auth";
import { useNavigate } from "react-router";

const AuthContext = createContext();

const AuthProvider = ({ children }) => {

    const [user, setUser] = useState(null);
    const [token, setToken] = useState("");
    const navigate = useNavigate();

    const fetchUserDetails = () => {
        // console.log("fetching user details...");
        const user = JSON.parse(localStorage.getItem("user"));
        if(user){
            setUser(user);
            setToken(user.jwtToken);
        }
    }

    useEffect(() => {
        fetchUserDetails();
    }, []);

    const signIn = async (data) => {
        try {
            const response = await signInApiCall(data);
            console.log(response);

            if (response.data) {
                response.data.jwtToken = "Bearer " + response.data.jwtToken;
                setUser(response.data);
                try{
                    localStorage.setItem("user", JSON.stringify(response.data));
                } catch (err) {
                    console.log(err.message);       
                }
                setToken(response.data.jwtToken);
                return;
            }
            throw new Error(response.message);
        } catch (err) {
            console.log(err);
        }

    }

    const signOut = () => {
        try{
            localStorage.setItem("user", null);
        } catch (err) {
            console.log(err.message);
        }
        setUser(null);
        setToken("");
        // console.log("Signing Out...", user  );
        navigate("/login");

    };

    return (
        <AuthContext.Provider value={{ user, token, signIn, signOut }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider;

export const useAuth = () => {
    return useContext(AuthContext)
}