import './App.scss'
import {Navigate, Routes, Route} from "react-router-dom";
import Lobby from './pages/Lobby';
import Game from "./pages/Game.tsx";

export default function App() {

    return (
        <>
            <Routes>
                <Route path={"/"}
                       element={<Navigate to={"/lobby"}/>}
                />
                <Route path={"/lobby"}
                       element={<Lobby/>}
                />
                <Route path={"/game"}
                       element={<Navigate to={"/game/:id"}/>}
                />
                <Route path={"/game/:id"}
                       element={<Game/>}
                />
                <Route path={"/*"}
                       element={<Navigate to={"/lobby"}/>}
                />
            </Routes>
        </>
    )
}
