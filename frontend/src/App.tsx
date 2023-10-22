import './App.scss'
import {Navigate, Routes, Route} from "react-router-dom";
import Lobby from './pages/Lobby/Lobby.tsx';
import Game from "./pages/Game/Game.tsx";

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
                       element={<Navigate to={"/lobby"}/>}
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
