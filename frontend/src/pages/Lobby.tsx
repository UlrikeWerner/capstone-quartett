import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function Lobby() {
    const navigate = useNavigate();

    function createNewGame() {
        axios.post("api/game")
            .then(response => {
                navigate('/game/' + response.data);
            })
            .catch(reason => {
                console.error(reason);
            })
    }

    return (
        <>
            <h1>NFL Quartett</h1>
            <h2>Lobby</h2>
            <button onClick={createNewGame}>
                NEW GAME
            </button>
        </>
    )
}