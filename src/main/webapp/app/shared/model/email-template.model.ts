export interface IEmailTemplate {
  id?: string;
  holidayEmailTemplate?: string;
}

export class EmailTemplate implements IEmailTemplate {
  constructor(public id?: string, public holidayEmailTemplate?: string) {}
}
