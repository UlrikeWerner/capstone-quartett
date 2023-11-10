import "./FootballButton.scss";

type FootballButtonProps = {
    readonly border?: boolean;
    readonly text: string;
    readonly buttonClick: () => void;
}
export default function FootballButton(props: FootballButtonProps) {

    return (
        <button className={props.border ? "football-button football-button-border" : "football-button"}
                type="button"
                onClick={props.buttonClick}
        >
            {props.text}
        </button>
    );
}