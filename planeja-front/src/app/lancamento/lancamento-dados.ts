export type TipoLancamento = 'RECEITA' | 'DESPESA';

export class DadosLancamentoForm {
  categoriaId!: number;
  cartaoId!: number | null;
  valor!: number;
  dataLancamento!: string;
  tipoLancamento!: TipoLancamento;
}

export class DetalhesLancamento {
  id!: string;
  dataLancamento!: string;
  valor!: number;
  tipoLancamento!: TipoLancamento;
  categoriaId!: number;
  categoriaNome!: string;
  cartaoId?: number | null;
  cartaoNome?: string;
}

export class FiltroLancamento {
  mes?: string;
  categoriaId?: number;
  cartaoId?: number;
  tipoLancamento?: TipoLancamento;
}
