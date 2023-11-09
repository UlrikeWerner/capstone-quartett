import axios from "axios";
import {useNavigate} from "react-router-dom";
import "./lobby.scss";
import FootballButton from "../../Component/Buttons/FootballButton.tsx";
import {useState} from "react";
import Dialog from "../../Component/Dialog/Dialog.tsx";
import Backdrop from "../../Component/Dialog/Backdrop.tsx";
import {GameInstructionText} from "../../types/GameIndrustion.tsx";

export default function Lobby() {
    const navigate = useNavigate();
    const [isOpen, setIsOpen] = useState<boolean>(false);

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
                        <FootballButton text="Neues Spiel"
                                        buttonClick={createNewGame}
                        />
                    </div>
                    <div className="football-basic-line-class stitch-button-line stitch-button-2">
                        <FootballButton text="Fortsetzen"
                                        buttonClick={() => navigate("/game")}
                        />
                    </div>
                    <div className="football-basic-line-class stitch-button-line stitch-button-3">
                        <FootballButton text="Anleitung"
                                        buttonClick={() => setIsOpen(true)}
                        />
                    </div>
                </div>
            </div>
            <Dialog open={isOpen}
                    content={GameInstructionText}
                    buttonName="Schließen"
                    buttonFunction={() => setIsOpen(false)}
            />
            <Backdrop open={isOpen} />
        </article>
    )
}