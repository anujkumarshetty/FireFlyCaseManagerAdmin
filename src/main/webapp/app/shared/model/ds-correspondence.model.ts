export interface IDSCorrespondence {
  id?: number;
  templateid?: number;
  lettertype?: string;
  category?: string;
  subcategory?: string;
  lettertemplateContentType?: string;
  lettertemplate?: any;
  isactive?: number;
  parentid?: number;
  templatetype?: string;
}

export const defaultValue: Readonly<IDSCorrespondence> = {};
