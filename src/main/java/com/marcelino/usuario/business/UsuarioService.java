package com.marcelino.usuario.business;

import com.marcelino.usuario.business.converter.UsuarioConverter;
import com.marcelino.usuario.business.dto.UsuarioDTO;
import com.marcelino.usuario.infrastructure.ConflictException;
import com.marcelino.usuario.infrastructure.ResourceNotFoundException;
import com.marcelino.usuario.infrastructure.entity.Usuario;
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

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email));
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
}
