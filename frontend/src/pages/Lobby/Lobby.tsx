import axios from "axios";
import {useNavigate} from "react-router-dom";
import "./lobby.scss";

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
        <article className="lobby">
            <h1>NFL Quartett 2022/23</h1>
            <div className="football-menu">
                <div className="football-basic-line-class football-line top-line"></div>
                <div className="football-basic-line-class football-line bottom-line"></div>
                <div className="football-basic-line-class football-stitching">
                    <div className="football-basic-line-class stitch-button-line">
                        <button className="football-button" onClick={createNewGame}>
                            Neues Spiel
                        </button>
                    </div>
                    <div className="football-basic-line-class stitch-button-line stitch-button-2"></div>
                    <div className="football-basic-line-class stitch-button-line stitch-button-3"></div>
                </div>
            </div>

        </article>
    )
}