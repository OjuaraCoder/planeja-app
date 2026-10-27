export type TipoLancamento = 'RECEITA' | 'DESPESA';

export class DadosLancamentoForm {
  categoriaId!: number;
  cartaoId!: number | null;
  descricao!: string;
  valor!: string;
  dataLancamento!: string;
  tipoLancamento!: TipoLancamento;
}

export class DetalhesLancamento {
  id!: string;
  dataLancamento!: string;
  valor!: string;
  tipoLancamento!: TipoLancamento;
  categoriaId!: number;
  categoriaNome!: string;
  cartaoId?: number | null;
  cartaoNome?: string;
  descricao?: string;
}

export class FiltroLancamento {
  mes?: string;
  categoriaId?: number;
  cartaoId?: number;
  tipoLancamento?: TipoLancamento;
}
