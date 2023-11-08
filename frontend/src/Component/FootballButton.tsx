import "./FootballButton.scss";

type FootballButtonProps = {
    readonly text: string;
    readonly buttonClick: () => void;
}
export default function FootballButton(props: FootballButtonProps) {

    return (
        <button className="button"
                type="button"
                onClick={props.buttonClick}
        >
            {props.text}
        </button>
    );
}