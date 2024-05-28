export interface Student {
  nome: string;
  esercizio: string;
  inizio: string;
  fine: string;
}

export interface Assignment {
  nome: string;
  data: string;
  studenti: Student[];
}
