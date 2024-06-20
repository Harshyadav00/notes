import axios from "axios"

const baseURL = process.env.REACT_APP_API_BASE_URL ;

export const signInApiCall = async (props) => {
    try {
        const response = await axios.post(`${baseURL}/api/auth/login`,
            {
                userName: props.email,
                password: props.password,
            }
        )

        return response ;


    } catch (e) {
        const msg = e?.response?.error.message ?? e?.message ?? 'Unknown Error';

        console.error(msg);

    }
}

export const signUp = async (props) => {
    try {
        const auth = await axios.post(`${baseURL}/api/auth/register`,
            {
                name: props.name,
                userName: props.email,
                email: props.email,
                password: props.password,
            }
        )

        return auth;
    } catch (e) {
        const msg = e?.response?.error.message ?? e?.message ?? 'Unknown Error';

        console.error(msg);

    }
}

