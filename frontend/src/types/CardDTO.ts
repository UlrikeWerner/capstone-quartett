import {NflLogoAcronym} from "./TeamLogo.tsx";

export type CardDTO = {
    name: string;
    logo: NflLogoAcronym;
    attributes: Record<string, string>;
}