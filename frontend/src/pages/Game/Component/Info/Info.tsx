import "./Info.scss";

type InfoProps = {
    readonly infoText: string;
    readonly instructionText: string;
}
export default function Info(props: InfoProps) {

    return (
        <div className="gameInfo">
            <h2>{props.infoText}</h2>
            <p>{props.instructionText}</p>
        </div>
    );
}