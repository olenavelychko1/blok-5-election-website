import {ISortDirection} from "@/interfaces/ISortDirection";

export default interface ISortState {
     property: string | null;
     direction: ISortDirection;
}