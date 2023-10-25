import "./Info.scss";

type InfoProps = {
    infoText: string;
    instructionText: string;
    nextTurn: "PLAYER" | "OPPONENT" | undefined;
}
export default function Info(props: InfoProps) {

    return (
        <div className="gameInfo">
            <h2>{props.infoText}</h2>
            <p>{props.instructionText}</p>
        </div>
    );
}