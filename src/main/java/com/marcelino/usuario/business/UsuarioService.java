package com.marcelino.usuario.business;

import com.marcelino.usuario.business.converter.UsuarioConverter;
import com.marcelino.usuario.business.dto.EnderecoDTO;
import com.marcelino.usuario.business.dto.TelefoneDTO;
import com.marcelino.usuario.business.dto.UsuarioDTO;
import com.marcelino.usuario.infrastructure.Exceptions.ConflictException;
import com.marcelino.usuario.infrastructure.Exceptions.ResourceNotFoundException;
import com.marcelino.usuario.infrastructure.entity.Endereco;
import com.marcelino.usuario.infrastructure.entity.Telefone;
import com.marcelino.usuario.infrastructure.entity.Usuario;
import com.marcelino.usuario.infrastructure.repository.EnderecoRepository;
import com.marcelino.usuario.infrastructure.repository.TelefoneRepository;
import com.marcelino.usuario.infrastructure.repository.UsuarioRepository;
import com.marcelino.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository  usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvaUsuario (UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void emailExiste ( String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("Email já cadastrado: " + email);
            }
        }catch(ConflictException e ){
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return  usuarioRepository.existsByEmail(email);
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado" + email)));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado" + email);
        }
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto){
        //aqui buscamos o email do usuario atraves do token(tira a obrigatoriedade do email )
        String email = jwtUtil.extractUsername(token.substring(7));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null );
        //buscamos dados do usuario no banco
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(()->
            new ResourceNotFoundException("E-mail não encontrado; atualizaDadosUsuario(); email-> " + email)
        );

        //mesclou os dados que recebemos na req DTO com os dados do banco
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        //Salvou os dados do usuario convertido e depois pegou o retorno e converteu para usuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO autualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id nao Encontrado; autualizaEndereco(); id: " + idEndereco)
        );

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }


    public TelefoneDTO autalizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(()->
            new ResourceNotFoundException("Id nao Encontrado; autalizaTelefone(); id: " + idTelefone)
        );

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));


    }


    public EnderecoDTO cadastraEndereco (String token, EnderecoDTO dto ){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("email nao encontrado; cadastraEndereco(); email: " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
    }

    public TelefoneDTO cadastraTelefone (String token, TelefoneDTO dto ){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("email nao encontrado; cadastraEndereco(); email: " + email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        Telefone telefoneEntity = telefoneRepository.save(telefone);
        return usuarioConverter.paraTelefoneDTO(telefoneEntity);
    }
}
