export interface IConfig {
  id?: string;
  holidayEmailSendDate?: number;
  holidayEmailNotification?: boolean;
  welcomingEmailNotification?: boolean;
  birthdayEmailNotification?: boolean;
}

export class Config implements IConfig {
  constructor(
    public id?: string,
    public holidayEmailSendDate?: number,
    public holidayEmailNotification?: boolean,
    public welcomingEmailNotification?: boolean,
    public birthdayEmailNotification?: boolean
  ) {
    this.holidayEmailNotification = this.holidayEmailNotification || false;
    this.welcomingEmailNotification = this.welcomingEmailNotification || false;
    this.birthdayEmailNotification = this.birthdayEmailNotification || false;
  }
}
