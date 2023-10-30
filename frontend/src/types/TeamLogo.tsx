import * as NFLIcons from "react-nfl-logos";
import {ten} from "./TennesseeTitansLogo.tsx";

export type NflLogoAcronym = "ARI" | "ATL" | "BAL" | "BUF" | "CAR" | "CHI" | "CIN" | "CLE" | "DAL" | "DEN" | "DET" |
    "GB" | "HOU" | "IND" | "JAX" | "KC" | "LAC" | "LAR" | "LV" | "MIA" | "MIN" | "NE" | "NFL" | "NO" | "NYG" | "NYJ" |
    "PHI" | "PIT" | "SEA" | "SF" | "TB" | "TEN" | "WAS";

export function getTeamLogo(acronym: NflLogoAcronym){
    switch (acronym) {
        case "ARI":
            return <NFLIcons.ARI />;
        case "ATL":
            return <NFLIcons.ATL />;
        case "BAL":
            return <NFLIcons.BAL />;
        case "BUF":
            return <NFLIcons.BUF />;
        case "CAR":
            return <NFLIcons.CAR />;
        case "CHI":
            return <NFLIcons.CHI />;
        case "CIN":
            return <NFLIcons.CIN />;
        case "CLE":
            return <NFLIcons.CLE />;
        case "DAL":
            return <NFLIcons.DAL />;
        case "DEN":
            return <NFLIcons.DEN />;
        case "DET":
            return <NFLIcons.DET />;
        case "GB":
            return <NFLIcons.GB />;
        case "HOU":
            return <NFLIcons.HOU />;
        case "IND":
            return <NFLIcons.IND />;
        case "JAX":
            return <NFLIcons.JAX />;
        case "KC":
            return <NFLIcons.KC />;
        case "LAC":
            return <NFLIcons.LAC />;
        case "LAR":
            return <NFLIcons.LAR />;
        case "LV":
            return <NFLIcons.LV />;
        case "MIA":
            return <NFLIcons.MIA />;
        case "MIN":
            return <NFLIcons.MIN />;
        case "NE":
            return <NFLIcons.NE />;
        case "NFL":
            return <NFLIcons.NFL />;
        case "NO":
            return <NFLIcons.NO />;
        case "NYG":
            return <NFLIcons.NYG />;
        case "NYJ":
            return <NFLIcons.NYJ />;
        case "PHI":
            return <NFLIcons.PHI />;
        case "PIT":
            return <NFLIcons.PIT />;
        case "SEA":
            return <NFLIcons.SEA />;
        case "SF":
            return <NFLIcons.SF />;
        case "TB":
            return <NFLIcons.TB />;
        case "TEN":
            return ten();
        case "WAS":
            return <NFLIcons.WAS />;

    }
}