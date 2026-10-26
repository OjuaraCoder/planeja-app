import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { TipoLancamento } from '../lancamento-dados';
import { ToastrService } from 'ngx-toastr';
import { LancamentoService } from '../lancamento-service';
import { DetalhesCategoria } from '../../categoria/categoria-dados';
import { DetalhesCartao } from '../../cartoes/cartao-dados';
import { forkJoin } from 'rxjs';
import { Header } from '../../common/components/header/header';

interface CadastroLancamentoForm {
  categoriaId: FormControl<string>;
  dataLancamento: FormControl<string>;
  valor: FormControl<string>;
  tipoLancamento: FormControl<TipoLancamento | '' >;
  cartaoId: FormControl<string>;
}

@Component({
  selector: 'app-cadastro-lancamento',
  imports: [Header, ReactiveFormsModule],
  templateUrl: './cadastro-lancamento.html',
  styleUrl: './cadastro-lancamento.scss',
})
export class CadastroLancamento implements OnInit {
  form!: FormGroup<CadastroLancamentoForm>;

  toast = inject(ToastrService);
  service = inject(LancamentoService);
  categoriasAtivas: DetalhesCategoria[] = [];
  cartoesAtivos: DetalhesCartao[] = [];

  ngOnInit(): void {
    this.form = new FormGroup<CadastroLancamentoForm>({
      categoriaId: new FormControl<string>('', {
        nonNullable: true,
        validators: Validators.required,
      }),
      dataLancamento: new FormControl<string>('', {
        nonNullable: true,
        validators: Validators.required,
      }),
      valor: new FormControl<string>('', {
        nonNullable: true,
        validators: Validators.required,
      }),
      tipoLancamento: new FormControl<TipoLancamento | ''>('', {
        nonNullable: true,
        validators: Validators.required,
      }),
      cartaoId: new FormControl<string>('', { nonNullable: true }),
    });

    this.inicializarDropdowns();
  }

  inicializarDropdowns(): void {
    forkJoin({
      categorias: this.service.listarCategoriasDisponiveis(),
      cartoes: this.service.listarCartoesDisponiveis(),
    }).subscribe({
      next: (resultado) => {
        this.categoriasAtivas = resultado.categorias;
        this.cartoesAtivos = resultado.cartoes;
      },
      error: () => {
        this.toast.error('Erro ao carregar dropdowns');
      },
    });
  }

  protected handleSubmit() {

  }
}
