export interface Student {
  nome: string;
  esercizio: string;
  inizio: string;
  fine: string;
  consegnato: boolean;
}

export interface Assignment {
  nome: string;
  data: string;
  studenti: Student[];
  tipo: string;
}
