import { Moment } from 'moment';

export const enum ContractType {
  SIVP = 'SIVP',
  KARAMA = 'KARAMA',
  CDD = 'CDD',
  CDI = 'CDI',
  SUMMER_TRAINEESHIP = 'SUMMER_TRAINEESHIP',
  FINAL_YEAR_TRAINEESHIP = 'FINAL_YEAR_TRAINEESHIP',
  FREELANCER = 'FREELANCER'
}

export interface IContract {
  id?: string;
  type?: ContractType;
  startDate?: Moment;
  endDate?: Moment;
  employeeSignatureDate?: Moment;
  directionSignatureDate?: Moment;
  description?: string;
  dayOffNamber?: number;
}

export class Contract implements IContract {
  constructor(
    public id?: string,
    public type?: ContractType,
    public startDate?: Moment,
    public endDate?: Moment,
    public employeeSignatureDate?: Moment,
    public directionSignatureDate?: Moment,
    public description?: string,
    public dayOffNamber?: number
  ) {}
}
