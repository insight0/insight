export interface ISkill {
  id?: string;
  name?: string;
}

export class Skill implements ISkill {
  constructor(public id?: string, public name?: string) {}
}
