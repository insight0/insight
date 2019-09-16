export interface IConfig {
  id?: string;
  holidayEmailSend?: number;
  newUserWelcommingEmail?: boolean;
}

export class Config implements IConfig {
  constructor(public id?: string, public holidayEmailSend?: number, public newUserWelcommingEmail?: boolean) {
    this.newUserWelcommingEmail = this.newUserWelcommingEmail || false;
  }
}
