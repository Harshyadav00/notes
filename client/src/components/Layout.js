import { Outlet } from "react-router";
import Header from "./Header";
import AuthProvider from "../actions/AuthProvider";

const Layout = () => {
    return (
        <AuthProvider>
            <Header />
            <Outlet />
        </AuthProvider>
    )
}

export default Layout;