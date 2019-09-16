export interface IDocumentPlaceholder {
  id?: string;
  name?: string;
  description?: string;
  version?: string;
  url?: string;
  type?: string;
  size?: string;
}

export class DocumentPlaceholder implements IDocumentPlaceholder {
  constructor(
    public id?: string,
    public name?: string,
    public description?: string,
    public version?: string,
    public url?: string,
    public type?: string,
    public size?: string
  ) {}
}
