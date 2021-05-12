package com.mygdx.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Jogo extends ApplicationAdapter {
	//cria uma variavel batch
	SpriteBatch batch;
	//Array de Texture para as posiçoes do passaro
	Texture[] passaros;
	//criando uma Texture para o fundo
	Texture fundo;
	//criando uma Texture para o cano de baixo
	Texture canobaixo;
	//criando uma Texture para o cano do topo
	Texture canotopo;

	//variaveis float para largura e altura
	private float larguradispositivo;
	private float alturadispositivo;

	//Variveis para movimentação dos canos
	private int movimentacanotopoY = 1080;
	private int movimentacanotopoX = 1080;
	private int movimentacanobaixoY = -100;
	private int movimentacanobaixoX = 1080;

	//Variveis para movimentação do passaro
	private int movimentaY = 0;
	private int movimentaX = 0;
	float variacao= 0;
	float gravidade = 0;
	float posicaoInicialVerticalPassaro= 0;

	@Override
	public void create () {
		//declara uma nova batch
		batch = new SpriteBatch();
		//metodo de declarações das informações no array
		DeclaroesInfArray();
		//declaração das imagens em cada texture criado
		DeclaracoesTextureImg();
		//metodo das variaveis com tamanho da tela
		Tamanhodatela();
		posicaoInicialVerticalPassaro= alturadispositivo / 2;
	}

	private void Tamanhodatela() {
		//pega a largura do dispositivo
		larguradispositivo = Gdx.graphics.getWidth();
		//pega a altura do dispositivo
		alturadispositivo = Gdx.graphics.getHeight();
		//coloca o valor na posição inial que é a altura do dispositivo divido por 2 que seria o meio da tela
	}

	private void DeclaracoesTextureImg() {
		//pega o assets do fundo e coloca na Texture fundo
		fundo = new Texture("fundo.png");
		//pega o assets do cano de baixo e coloca na Texture canobaixo
		canobaixo = new Texture("cano_baixo_maior.png");
		//pega o assets do cano do topo e coloca na Texture canotopo
		canotopo = new Texture("cano_topo_maior.png");
	}

	private void DeclaroesInfArray() {
		//aplicando um tamanho para o Array dos passaros
		passaros = new Texture[3];
		//colando as imagem no array
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");
	}

	@Override
	public void render () {
		//inicia a renderização
		batch.begin();
		//condicao para variavel variacao se ela for maior que 3 ela é zerada
		if(variacao >3)
			//zerando a variavel variacao
			variacao = 0;
		//metodo toque na tela
		ToqueTela();
		//metodo de setar imagens na tela e movimentação
		SetImgTelaMov();
		//metodo dos contadores
		Contadores();
		//termina a seguencia a aplicação
		batch.end();
	}

	private void Contadores() {
		//contador de gravidade
		gravidade ++;
		//contador x
		movimentacanobaixoX--;
		movimentacanotopoX--;
		movimentaX++;
		//contador y
		movimentacanobaixoY--;
		movimentacanotopoY--;
		movimentaY++;
	}

	private void SetImgTelaMov() {
		//desenha a imagem puxada do create
		batch.draw(fundo, 0, 0,larguradispositivo,alturadispositivo);
		//desenha o personagem na tela e faz a movimentação trocando as assets
		batch.draw(passaros[(int) variacao], 30, posicaoInicialVerticalPassaro);
		//desenha o cano de baixo na tela e faz a movimentação;
		batch.draw(canobaixo, movimentacanobaixoX, movimentacanobaixoY);
		//desenha o cano de cima na tela e faz a movimentação;
		batch.draw(canotopo, movimentacanotopoX, movimentacanotopoY);
		//tempo para variação das mudanças da animação do passaro
		variacao += Gdx.graphics.getDeltaTime() * 10;
	}

	private void ToqueTela() {
		//cria uma booleana para chamar o metodo de toque na tela
		boolean toqueTela = Gdx.input.justTouched();
		//caso não tenha toque na tela aplica uma gravidade -25
		if (Gdx.input.justTouched()){
			gravidade = -25;
		}
		//if para que sempre q tocar na tela o pesonagem sair e sair da posiçao 0
		if(posicaoInicialVerticalPassaro > 0 || toqueTela)
			// faz a posiçao vertical menos a gravidade para fazer o passaro cair
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;
	}

	@Override
	public void dispose () {

	}
}
