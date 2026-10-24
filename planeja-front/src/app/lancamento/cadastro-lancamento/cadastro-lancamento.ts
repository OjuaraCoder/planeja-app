import { Component, inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { TipoLancamento } from '../lancamento-dados';
import { ToastrService } from 'ngx-toastr';
import { LancamentoService } from '../lancamento-service';
import { DetalhesCategoria } from '../../categoria/categoria-dados';
import { DetalhesCartao } from '../../cartoes/cartao-dados';

interface CadastroLancamentoForm {
  categoriaId: FormControl<string>;
  dataLancamento: FormControl<string>;
  valor: FormControl<string>;
  tipoLancamento: FormControl<TipoLancamento | '' >;
  cartaoId: FormControl<string>;
}

@Component({
  selector: 'app-cadastro-lancamento',
  imports: [],
  templateUrl: './cadastro-lancamento.html',
  styleUrl: './cadastro-lancamento.scss',
})
export class CadastroLancamento {
  form!: FormGroup<CadastroLancamentoForm>;
  toast = inject(ToastrService);
  service = inject(LancamentoService);

  categoriasAtivas: DetalhesCategoria[] = [];
  cartoesAtivos: DetalhesCartao[] = []

}
